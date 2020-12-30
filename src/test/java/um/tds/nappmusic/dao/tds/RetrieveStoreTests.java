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

  private PersistencyWrapper wrapper;

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
    wrapper.registerEntity(entity);
    Entidad retrieved = wrapper.retrieveEntity(entity.getId());
    assertNotNull(retrieved);
    assertEquals(entity.getId(), retrieved.getId(), "Stored and retrieved entity Ids do not match");
    return retrieved;
  }

  @BeforeEach
  void initAll() {
    wrapper = new PersistencyWrapper();
  }

  @Test
  void checkEmptyEntity() {
    Entidad entity = createEntity();
    registerAndRetrieve(entity);
  }

  @Test
  void checkStringField() {
    String FIELD_VALUE = "value";
    Entidad entity = createEntity(new Propiedad(FIELD_NAME, FIELD_VALUE));

    Entidad rentity = registerAndRetrieve(entity);
    String rvalue = wrapper.retrieveString(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE, rvalue, "Incorrect stringProperty");
  }

  @Test
  void checkBooleanField() {
    boolean FIELD_VALUE = true;
    Entidad entity = createEntity(new Propiedad(FIELD_NAME, wrapper.encodeBoolean(FIELD_VALUE)));

    Entidad rentity = registerAndRetrieve(entity);
    boolean rvalue = wrapper.retrieveBoolean(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE, rvalue, "Incorrect booleanProperty");
  }

  @Test
  void checkIdField() {
    Entidad referenced = registerAndRetrieve(createEntity());
    Propiedad p =
        new Propiedad(
            FIELD_NAME,
            wrapper.encodeObject(
                new Identifiable() {
                  @Override
                  public int getId() {
                    return referenced.getId();
                  }

                  @Override
                  public void setId(int id) {}
                }));
    Entidad entity = createEntity(p);

    Entidad rentity = registerAndRetrieve(entity);
    int rvalue = wrapper.retrieveId(rentity, FIELD_NAME);
    assertEquals(referenced.getId(), rvalue, "Incorrect objectIdProperty");
  }

  @Test
  void checkIdListField() {
    Entidad referenced = registerAndRetrieve(createEntity());
    Identifiable referencedObj =
        new Identifiable() {
          @Override
          public int getId() {
            return referenced.getId();
          }

          @Override
          public void setId(int id) {}
        };
    List<Identifiable> FIELD_VALUE = Arrays.asList(referencedObj, referencedObj, referencedObj);
    Propiedad p = new Propiedad(FIELD_NAME, wrapper.encodeObjectList(FIELD_VALUE));
    Entidad entity = createEntity(p);

    Entidad rentity = registerAndRetrieve(entity);
    List<Integer> rvalue = wrapper.retrieveIdList(rentity, FIELD_NAME);
    assertEquals(FIELD_VALUE.size(), rvalue.size());
    for (int i = 0; i < FIELD_VALUE.size(); i++) {
      assertEquals(FIELD_VALUE.get(i).getId(), rvalue.get(i), "Incorrect objectIdCollection");
    }
  }
}
