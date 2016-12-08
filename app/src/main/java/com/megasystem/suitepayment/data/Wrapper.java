package com.megasystem.suitepayment.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import com.googlecode.openbeans.PropertyDescriptor;
import com.megasystem.suitepayment.Application;
import com.megasystem.suitepayment.entity.Action;
import com.megasystem.suitepayment.entity.Entity;
import com.megasystem.suitepayment.entity.annotation.Ignore;
import com.megasystem.suitepayment.entity.annotation.Key;
import com.megasystem.suitepayment.entity.annotation.Nullable;
import com.megasystem.suitepayment.entity.sale.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by aortuno on 9/15/2015.
 */
public abstract class Wrapper<T> extends SQLiteOpenHelper {

    protected Class<T> type;

    private SQLiteDatabase connection = null;

    protected Context context;

    public Wrapper(Context context, Class<T> type) {
        super(context, Application.DataBaseName + ".db", null, 1);
        this.type = type;
    }

    public Wrapper(Context context, SQLiteDatabase connection, Class<T> type) {
        super(context, "database.db", null, 1);
        this.connection = connection;
        this.type = type;
    }

    public void clean(Entity entity) {
        SQLiteDatabase objDb = this.getWritableDatabase();
        String table = entity.getClass().getCanonicalName().replace(entity.getClass().getPackage().getName() + ".", "").toLowerCase();
        try {
            objDb.beginTransaction();
            objDb.delete(table, "", new String[]{});
            objDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
        } finally {
            objDb.endTransaction();
            objDb.close();
        }
    }

    private static boolean Ignore(Field field) {
        Annotation[] annotations = field.getAnnotations();
        if (annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Ignore) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean save(Entity entity) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        String table = entity.getClass().getCanonicalName().replace(entity.getClass().getPackage().getName() + ".", "").toLowerCase();
        try {
            db.beginTransaction();
            values = new ContentValues();
            Field[] fields = entity.getClass().getDeclaredFields();
            Field[] superFields = entity.getClass().getSuperclass().getDeclaredFields();
            List<Field> listFields = new ArrayList<Field>();
            listFields.addAll(Arrays.asList(fields));
            listFields.addAll(Arrays.asList(superFields));
            fields = new Field[listFields.size()];
            fields = listFields.toArray(fields);
            for (Field field : fields) {
                PropertyDescriptor prop;
                try {
                    prop = new PropertyDescriptor(field.getName(), entity.getClass());
                } catch (NullPointerException e) {
                    prop = new PropertyDescriptor(field.getName(), entity.getClass().getSuperclass());
                }
                Method method = prop.getReadMethod();
                Object value = method.invoke(entity);
                if (value != null) {
                    if (!Ignore(field)) {
                        values.put(field.getName(), value.toString());
                    }
                }
            }
            if (entity.getAction().equals(Action.Insert)) {
                entity.setId(db.insert(table, null, values));
            } else if (entity.getAction().equals(Action.Update)) {
                db.update(table, values, "id=?", new String[]{Long.toString(entity.getId())});
            } else {
                db.delete(table, "id=?", new String[]{Long.toString(entity.getId())});
            }
            db.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return result;
    }

    public static String getCreate(Entity entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Field[] superFields = entity.getClass().getSuperclass().getDeclaredFields();
        List<Field> listFields = new ArrayList<Field>();
        listFields.addAll(Arrays.asList(fields));
        listFields.addAll(Arrays.asList(superFields));
        fields = new Field[listFields.size()];
        fields = listFields.toArray(fields);
        List<Object> columns = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(entity.getClass().getCanonicalName().replace(entity.getClass().getPackage().getName() + ".", "").toLowerCase());
        try {
            for (Field field : fields) {
                if (!(field.getName().equals("action"))) {
                    if (!Ignore(field)) {
                        Annotation[] annotations = field.getAnnotations();
                        if (annotations.length > 0) {
                            for (Annotation annotation : annotations) {
                                if (annotation instanceof Key) {
                                    columns.add(field.getName() + " integer NOT NULL PRIMARY KEY AUTOINCREMENT");
                                } else if (annotation instanceof Nullable) {
                                    columns.add(field.getName() + " text");
                                } else {
                                    columns.add(field.getName() + " text NOT NULL");
                                }
                            }
                        } else {
                            columns.add(field.getName() + " text");
                        }
                    }
                }

            }
            builder.append(concatKeys(columns));
            return builder.toString();
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
        }
        return "";
    }

    protected List<T> list(String query) {
        List<T> lstResult = new ArrayList<T>();
        SQLiteDatabase objDb = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = objDb.rawQuery(query, new String[]{});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    lstResult.add(this.load(cursor));
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        objDb.close();
        this.close();
        return lstResult;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected List<Map> genericList(String strQuery) {
        List<Map> lstResult = new ArrayList<Map>();
        Map obj;
        SQLiteDatabase objDb = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = objDb.rawQuery(strQuery, new String[]{});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    obj = new HashMap();
                    for (int index = 0; index < cursor.getColumnCount(); index++) {
                        obj.put(cursor.getColumnName(index), cursor.getString(index));
                    }
                    lstResult.add(obj);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        objDb.close();
        this.close();
        return lstResult;
    }

    public T load(Cursor cursor) {
        T obj = null;
        try {
            obj = type.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
            List<Field> listFields = new ArrayList<Field>();
            listFields.addAll(Arrays.asList(fields));
            listFields.addAll(Arrays.asList(superFields));
            for (Field field : listFields) {
                if (!Ignore(field)) {
                    PropertyDescriptor prop;
                    try {
                        prop = new PropertyDescriptor(field.getName(), obj.getClass());
                    } catch (NullPointerException e) {
                        prop = new PropertyDescriptor(field.getName(), obj.getClass().getSuperclass());
                    }
                    Method method = prop.getWriteMethod();

                    if (method != null) {
                        Type type = field.getGenericType();
                        if (type.toString().equals(String.class.toString())) {
                            String value = cursor.getString(cursor.getColumnIndex(field.getName()));
                            method.invoke(obj, value);
                        } else if (type.toString().equals(Long.class.toString())) {
                            Long value = cursor.getLong(cursor.getColumnIndex(field.getName()));
                            method.invoke(obj, value);
                        } else if (type.toString().equals(Integer.class.toString())) {
                            Integer value = cursor.getInt(cursor.getColumnIndex(field.getName()));
                            method.invoke(obj, value);
                        } else if (type.toString().equals(Double.class.toString())) {
                            Double value = cursor.getDouble(cursor.getColumnIndex(field.getName()));
                            method.invoke(obj, value);
                        } else if (type.toString().equals(Date.class.toString())) {
                            String value = cursor.getString(cursor.getColumnIndex(field.getName()));
                            if (value != null) {
                                Date dateValue = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US).parse(value);
                                method.invoke(obj, dateValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    protected static String concatKeys(List<Object> Llaves) {
        return ((Llaves.size() > 0) ? "(" + TextUtils.join(", ", Llaves) + ")" : "(o)");
    }

    public T get(String strQuery) {
        SQLiteDatabase objDb = this.getReadableDatabase();
        Cursor cursor = null;
        T entity = null;
        try {
            entity = type.newInstance();
            cursor = objDb.rawQuery(strQuery, new String[]{});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    entity = this.load(cursor);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(Application.tag, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        objDb.close();
        this.close();
        return entity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(getCreate(new Customer()));
        db.execSQL(getCreate(new User()));
        db.execSQL(getCreate(new Empleado()));
        db.execSQL(getCreate(new MsClasificador()));
        db.execSQL(getCreate(new PsClasificador()));
        db.execSQL(getCreate(new Pago()));
        db.execSQL(getCreate(new Gasto()));
        db.execSQL(getCreate(new HistorialPagos()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public <T extends Entity> String extract(List<T> list, String metodo) throws QueryException {
        List<Grouping<Object, T>> grouped = (List<Grouping<Object, T>>) CQ
                .<T, T> query(list).group().groupBy(metodo).list();
        String key = CQ
                .<Grouping<Object, T>, Object> query()
                .from(grouped)
                .transformDirect(
                        new ResultTransformer<Grouping<Object, T>, Object>() {
                            @Override
                            public Object transform(Grouping<Object, T> arg0) {
                                return arg0.getKey();
                            }
                        }).list().toString().replaceAll("\\[|\\]", "");
        return "(" + ((key.isEmpty()) ? "0" : key) + ")";

    }*/
    public SQLiteDatabase getConnection() {
        return connection;
    }

    public void setConnection(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public void openTransaction() {
        connection = this.getWritableDatabase();
        connection.beginTransaction();
    }

    public void transactionSuccesfully() {
        connection.setTransactionSuccessful();
    }

    public void closeTransaction() {
        connection.endTransaction();
        connection.close();
    }
}