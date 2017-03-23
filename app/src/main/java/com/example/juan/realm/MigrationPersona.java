package com.example.juan.realm;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Juan on 23/03/2017.
 */

public class MigrationPersona implements RealmMigration{
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {

            // Create a new class
            RealmObjectSchema perSchema = schema.get("Persona");
            perSchema.addField("edat",int.class);
            perSchema.addIndex("edat")
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("edat", obj.getString("dataNaixement"));
                        }
                    });

            oldVersion++;
        }
    }


}
