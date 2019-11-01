package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.model.map.elements.MapElement;
import com.unicornstudio.lanball.model.map.elements.RectangleElement;
import com.unicornstudio.lanball.service.StageService;
import com.unicornstudio.lanball.util.adapter.EditorElementTypesListAdapter;
import com.unicornstudio.lanball.util.adapter.EditorElementsListAdapter;
import com.unicornstudio.lanball.util.adapter.dto.EditorElement;
import com.unicornstudio.lanball.util.adapter.dto.EditorElementListRow;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Editor extends AbstractLmlView {

    @LmlActor("verticalGroup")
    private VerticalGroup verticalGroup;

    @LmlActor("listViewContainer")
    private Container<Actor> listViewContainer;

    @LmlActor("createElementWindowListViewContainer")
    private Container<Actor> createElementWindowListViewContainer;

    @LmlActor("createElementButton")
    private Button createElementButton;

    @LmlActor("createElementWindow")
    private Window createElementWindow;

    private EditorElementTypesListAdapter<EditorElement> editorElementTypesListAdapter;

    private EditorElementsListAdapter<EditorElementListRow> elementsListAdapter;

    private EditorElementListRow currentEditorElementListRow;

    private ChangeListener changeListener;

    @Inject
    public Editor(StageService stageService) {
        super(stageService.getStage(true));
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Editor.lml");
    }

    @Override
    public String getViewId() {
        return "editor";
    }

    @LmlAfter
    public void after() {
        createElementSettingsByObjectClass(verticalGroup, RectangleElement.class);
        elementsListAdapter = new EditorElementsListAdapter<>(new Array<>(EditorElementListRow.class));
        listViewContainer.setActor(new ListView<>(elementsListAdapter).getMainTable());
        elementsListAdapter.setItemClickListener(
                new ListView.ItemClickListener<EditorElementListRow>() {
                    @Override
                    public void clicked(EditorElementListRow item) {
                        //createElementSettingsByObjectClass(verticalGroup, item.getEditorElement().getElementClass());
                        currentEditorElementListRow = item;
                        createElementSettingsByObjectClass(verticalGroup, item.getMapElement());
                        verticalGroup.removeListener(changeListener);
                        verticalGroup.addListener(changeListener);
                    }
                }
        );
        changeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("change");
                writeDataToObject(verticalGroup, currentEditorElementListRow.getMapElement());
            }
        };

    }

    @LmlAction("onCreateElementChange")
    public void onCreateElementChange() {
        editorElementTypesListAdapter = new EditorElementTypesListAdapter<>(new Array<>(EditorElement.class));
        createElementWindowListViewContainer.setActor(new ListView<>(editorElementTypesListAdapter).getMainTable());
        for (EditorElement e : EditorElement.values()) {
            editorElementTypesListAdapter.add(e);
        }
        getStage().addActor(createElementWindow);
        centerPosition(createElementWindow);
    }

    @LmlAction("onCreateElementOkButton")
    public void onCreateElementOkButton() {
        editorElementTypesListAdapter.getSelection().forEach(
                selection -> {
                    try {
                        if (isElementsCountIsInLimit(selection)) {
                            MapElement element = (MapElement) selection.getElementClass().newInstance();
                            if (element instanceof RectangleElement) {
                                ((RectangleElement) element).setX(32);
                                ((RectangleElement) element).setColor("DupaDUpa");
                                System.out.println("dzieje sie cos");
                            }
                            elementsListAdapter.add(new EditorElementListRow(elementsListAdapter.size() + 1, selection, element));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        onCreateElementCancelButton();
    }

    @LmlAction("onCreateElementCancelButton")
    public void onCreateElementCancelButton() {
        createElementWindow.remove();
    }

    private void createElementSettingsByObjectClass(VerticalGroup verticalGroup, Class<?> aClass) {
        verticalGroup.clear();
        for (Field f : (aClass.getDeclaredFields())) {
            HorizontalFlowGroup group = new HorizontalFlowGroup();
            group.setSpacing(5f);
            group.setDebug(true);
            group.setWidth(275f);
            Separator separator = new Separator();
            separator.setFillParent(true);
            group.addActor(separator);
            VisLabel label = new VisLabel(f.getName());
            group.addActor(label);
            if (f.getType().isEnum()) {
                VisSelectBox<String> selectBox = new VisSelectBox<>();
                selectBox.setItems(new Array<>(Stream.of(f.getType().getEnumConstants()).map(
                        Object::toString
                ).toArray(String[]::new)));
                group.addActor(selectBox);
            } else {
                group.addActor(new VisTextField(""));
            }
            verticalGroup.addActor(group);
        }
    }

    private void createElementSettingsByObjectClass(VerticalGroup verticalGroup, MapElement mapElement) {
        verticalGroup.clear();
        for (Field f : (mapElement.getClass().getDeclaredFields())) {
            HorizontalFlowGroup group = new HorizontalFlowGroup();
            group.setSpacing(5f);
            group.setWidth(275f);
            Separator separator = new Separator();
            separator.setFillParent(true);
            group.addActor(separator);
            VisLabel label = new VisLabel(f.getName());
            group.addActor(label);
            if (f.getType().isEnum()) {
                VisSelectBox<String> selectBox = new VisSelectBox<>();
                selectBox.setName(f.getName());
                selectBox.setItems(new Array<>(Stream.of(f.getType().getEnumConstants()).map(
                        Object::toString
                ).toArray(String[]::new)));
                group.addActor(selectBox);
            } else {
                try {
                    f.setAccessible(true);
                    VisTextField textField = new VisTextField(String.valueOf(f.get(mapElement)));
                    textField.setName(f.getName());
                    group.addActor(textField);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            verticalGroup.addActor(group);
        }
    }

    private void writeDataToObject(VerticalGroup verticalGroup, MapElement mapElement) {
        HorizontalFlowGroup group = (HorizontalFlowGroup) verticalGroup.getChild(0);
        for (Field f : (currentEditorElementListRow.getMapElement().getClass().getDeclaredFields())) {
            Actor actor = findByName(group.getChildren(), f.getName());
            if (actor instanceof VisTextField) {
                System.out.println(f.getName() + " " + f.getType() + " " + f.getAnnotatedType() + " " + f.getGenericType());
                f.setAccessible(true);
                try {
                    if (f.getType() == int.class) {
                        f.set(mapElement, Integer.valueOf(((VisTextField) actor).getText()));
                    } else if(f.getType() == String.class) {
                        f.set(mapElement, ((VisTextField) actor).getText());
                    } else if (f.getType().isEnum()) {

                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Actor findByName(SnapshotArray<Actor> actors, String name) {
        return Stream.of(actors.toArray())
                .filter(a -> name.equals(a.getName()))
                .findFirst()
                .orElse(null);
    }


    private void centerPosition(Actor actor) {
        actor.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Align.center);
    }

    private boolean isElementsCountIsInLimit(EditorElement editorElement) {
        if (editorElement.getInstanceLimit() == null) {
            return true;
        }
        AtomicInteger count = new AtomicInteger(0);
        elementsListAdapter.iterable().forEach(
                editorElementListRow -> {
                    if (editorElementListRow.getEditorElement().equals(editorElement)) {
                        count.incrementAndGet();
                    }
                }
        );
        return count.intValue() < editorElement.getInstanceLimit();
    }

}
