package um.tds.nappmusic.dao.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import beans.Entidad;
import beans.Propiedad;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.tds.nappmusic.dao.Identifiable;

class RetrieveStoreTests {
  private static final String ENTITY_NAME = "Entity";
  private static final String FIELD_NAME = "field";

  private DaoFactory factory;

  Entidad createEntity() {
    Entidad entity = new Entidad();
    entity.setNombre(ENTITY_NAME);
    return entity;
  }

  Entidad createEntity(Propiedad p) {
    Entidad entity = createEntity();
    entity.setPropiedades(Arrays.asList(p));
    return entity;
  }

  Entidad registerAndRetrieve(Entidad entity) {
    assertNotNull(entity);
    assertEquals(entity.getNombre(), ENTITY_NAME);
    factory.registerEntity(entity);
    Entidad retrieved = factory.retrieveEntity(entity.getId());
    assertNotNull(retrieved);
    assertEquals(entity.getId(), retrieved.getId(), "Stored and retrieved entity Ids do not match");
    return retrieved;
  }

  @BeforeEach
  void initAll() {
    factory = new DaoFactory();
  }

  @Test
  void checkEmptyEntity() {
    Entidad entity = createEntity();
    registerAndRetrieve(entity);
  }

  @Test
  void checkStringField() {
    String FIELD_VALUE = "value";
    Entidad entity = createEntity(factory.stringProperty(FIELD_NAME, FIELD_VALUE));

    Entidad rentity = registerAndRetrieve(entity);
    String rvalue = factory.retrieveString(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE, rvalue, "Incorrect stringProperty");
  }

  @Test
  void checkBooleanField() {
    boolean FIELD_VALUE = true;
    Entidad entity = createEntity(factory.booleanProperty(FIELD_NAME, FIELD_VALUE));

    Entidad rentity = registerAndRetrieve(entity);
    boolean rvalue = factory.retrieveBoolean(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE, rvalue, "Incorrect booleanProperty");
  }

  @Test
  void checkIdField() {
    Entidad referenced = registerAndRetrieve(createEntity());
    Propiedad p =
        factory.objectProperty(
            FIELD_NAME,
            new Identifiable() {
              public int getId() {
                return referenced.getId();
              }

              public void setId(int id) {}
            });
    Entidad entity = createEntity(p);

    Entidad rentity = registerAndRetrieve(entity);
    int rvalue = factory.retrieveId(rentity, FIELD_NAME);
    assertEquals(referenced.getId(), rvalue, "Incorrect objectIdProperty");
  }

  @Test
  void checkIdListField() {
    Entidad referenced = registerAndRetrieve(createEntity());
    Identifiable referencedObj =
        new Identifiable() {
          public int getId() {
            return referenced.getId();
          }

          public void setId(int id) {}
        };
    List<Identifiable> FIELD_VALUE = Arrays.asList(referencedObj, referencedObj, referencedObj);
    Propiedad p = factory.objectCollectionProperty(FIELD_NAME, FIELD_VALUE);
    Entidad entity = createEntity(p);

    Entidad rentity = registerAndRetrieve(entity);
    List<Integer> rvalue = factory.retrieveIdList(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE.size(), rvalue.size());
    for (int i = 0; i < FIELD_VALUE.size(); i++) {
      assertEquals(FIELD_VALUE.get(i).getId(), rvalue.get(i), "Incorrect objectIdCollection");
    }
  }
}
