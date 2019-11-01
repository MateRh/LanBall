package com.unicornstudio.lanball.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.actors.CircleActor;
import com.unicornstudio.lanball.model.actors.DiskActor;
import com.unicornstudio.lanball.model.actors.EdgeActor;
import com.unicornstudio.lanball.model.actors.RectangleActor;
import com.unicornstudio.lanball.model.entity.Circle;
import com.unicornstudio.lanball.model.entity.Disk;
import com.unicornstudio.lanball.model.entity.Edge;
import com.unicornstudio.lanball.model.entity.Rectangle;
import com.unicornstudio.lanball.model.map.elements.CircleElement;
import com.unicornstudio.lanball.model.map.elements.CollisionBits;
import com.unicornstudio.lanball.model.map.elements.DiskElement;
import com.unicornstudio.lanball.model.map.elements.EdgeElement;
import com.unicornstudio.lanball.model.map.elements.Element;
import com.unicornstudio.lanball.model.map.elements.FigureType;
import com.unicornstudio.lanball.model.map.elements.MapElement;
import com.unicornstudio.lanball.model.map.elements.RectangleElement;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EntityFactory {

    private final World world;

    public Entity create(MapElement mapElement) {

        switch (mapElement.getType()) {
            case DISK:
                return createDisk((DiskElement) mapElement);
            case CIRCLE:
                return createCircle((CircleElement) mapElement);
            case RECTANGLE:
                return createRectangle((RectangleElement) mapElement);
            case EDGE:
                return createEdge((EdgeElement) mapElement);
            default:
                Gdx.app.error("EntityFactory", "Not recognizable element of type: " + mapElement.getType());
                return null;
        }
    }

    private Disk createDisk(DiskElement diskElement) {
        DiskActor diskActor = new DiskActor(diskElement.getRadius(), diskElement.getBorderSize(), diskElement.getColor());
        setActorPosition(diskActor, diskElement, Align.center);
        return new Disk(diskActor,
                createPhysicsEntity(diskElement, BodyDef.BodyType.StaticBody, Shape.Type.Circle),
                diskElement.getFunctionalType());
    }

    private Circle createCircle(CircleElement circleElement) {
        CircleActor circleActor = new CircleActor(circleElement.getRadius(), circleElement.getLineThickness(), circleElement.getColor());
        setActorPosition(circleActor, circleElement, Align.center);
        return new Circle(circleActor,
                createPhysicsEntity(circleElement, BodyDef.BodyType.StaticBody, Shape.Type.Circle),
                circleElement.getFunctionalType());
    }

    private Rectangle createRectangle(RectangleElement rectangleElement) {
        RectangleActor rectangleActor = new RectangleActor(rectangleElement.getWidth(), rectangleElement.getHeight(), rectangleElement.getColor());
        setActorPosition(rectangleActor, rectangleElement);
        return new Rectangle(rectangleActor,
                createPhysicsEntity(rectangleElement, BodyDef.BodyType.StaticBody, Shape.Type.Polygon),
                rectangleElement.getFunctionalType());
    }

    private Edge createEdge(EdgeElement edgeElement) {
        EdgeActor edgeActor = new EdgeActor(edgeElement.getWidth(), edgeElement.getHeight(), edgeElement.getColor());
        setActorPosition(edgeActor, edgeElement);
        return new Edge(edgeActor,
                createPhysicsEntity(edgeElement, BodyDef.BodyType.StaticBody, Shape.Type.Edge),
                edgeElement.getFunctionalType()
        );
    }

    private PhysicsEntity createPhysicsEntity(Element element, BodyDef.BodyType bodyType, Shape.Type shapeType) {
        return new PhysicsEntityBuilder()
                .world(world)
                .shapeType(shapeType)
                .position(element.getX(), element.getY())
                .radius((float) element.getRadius())
                .width((float) element.getWidth())
                .height((float) element.getHeight())
                .bodyType(bodyType)
                .linearDamping(element.getLinearDamping())
                .fixedRotation(element.isFixedRotation())
                .friction(element.getFriction())
                .restitution(element.getRestitution())
                .density(element.getDensity())
                .categoryBits(Optional.ofNullable(element.getCategoryBits()).map(CollisionBits::getBit).orElse(null))
                .maskBits(Optional.ofNullable(element.getMaskBits()).map(this::getCategoryBits).orElse(null))
                .active(!element.getFigureType().equals(FigureType.GRAPHICAL_ONLY))
                .sensor(element.isSensor())
                .build();
    }

    private void setActorPosition(Actor actor, Element element, int align) {
        actor.setPosition(element.getX(), element.getY(), align);
    }

    private void setActorPosition(Actor actor, Element element) {
        actor.setPosition(element.getX(), element.getY());
    }

    private Short getCategoryBits(List<CollisionBits> collisionBits) {
        return collisionBits.stream()
                .map(CollisionBits::getBit)
                .reduce((s1, s2) -> (short) (s1 | s2))
                .orElse(null);
    }

}
