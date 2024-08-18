package edu.school21;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        String[] classNames = { "User", "Car" };

        System.out.println("Classes:");

        for (String name : classNames) {
            System.out.println("- " + name);
        }

        System.out.print("---------------------\nEnter class name:\n-> ");
        String inputClass = sc.nextLine();
        Class<?> classInput = Class.forName("edu.school21.classes." + inputClass);

        Field[] fields = classInput.getDeclaredFields();
        Method[] methods = classInput.getDeclaredMethods();

        displayFields(fields);
        displayMethods(methods);

        Object instance = createInstance(classInput, fields, sc);

        modifyField(instance, fields, sc);

        callMethod(instance, methods, sc);
    }

    private static void callMethod(Object instance, Method[] methods, Scanner sc) throws Exception {
        System.out.print("---------------------\nEnter name of the method to call:\n-> ");
        String methodName = sc.nextLine();
        for (Method method : methods) {
            String params = Arrays.toString(method.getParameterTypes());
            params = params.substring(1, params.length() - 1);

            if (methodName.equals(String.format("%s(%s)", method.getName(), params))) {
                Object[] paramValues = getMethodParams(method, sc);
                System.out.println("Method returned:\n" + method.invoke(instance, paramValues));
                break;
            }
        }
    }

    private static Object[] getMethodParams(Method method, Scanner sc) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            System.out.print("Enter value for " + paramTypes[i].getSimpleName() + ":\n-> ");
            if (paramTypes[i].equals(String.class)) {
                params[i] = sc.nextLine();
            } else if (paramTypes[i].equals(int.class) || paramTypes[i].equals(Integer.class)) {
                params[i] = sc.nextInt();
                sc.nextLine();
            } else if (paramTypes[i].equals(double.class) || paramTypes[i].equals(Double.class)) {
                params[i] = sc.nextDouble();
                sc.nextLine();
            } else if (paramTypes[i].equals(boolean.class) || paramTypes[i].equals(Boolean.class)) {
                params[i] = sc.nextBoolean();
                sc.nextLine();
            } else {
                System.out.println("Unsupported parameter type: " + paramTypes[i].getSimpleName());
            }
        }
        return params;
    }

    private static void modifyField(Object instance, Field[] fields, Scanner sc) throws Exception {
        System.out.print("---------------------\nEnter name of the field for changing:\n-> ");
        String fieldName = sc.nextLine();
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                System.out.printf("Enter new value (%s):\n-> ", field.getType().getSimpleName());
                setFieldValue(instance, field, sc);
            }
        }
        System.out.println("Updated object: " + instance.toString());
    }

    private static Object createInstance(Class<?> classInput, Field[] fields, Scanner sc) throws Exception {
        Constructor<?> constructor = classInput.getConstructor();
        Object instance = constructor.newInstance();
        System.out.println("---------------------\nLet's create an object.");

        for (Field field : fields) {
            System.out.printf("%s (%s):\n-> ", field.getName(), field.getType().getSimpleName());
            setFieldValue(instance, field, sc);
        }

        System.out.println("Object created: " + instance);

        return instance;
    }

    private static void setFieldValue(Object instance, Field field, Scanner sc) throws Exception {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();

        if (fieldType.equals(String.class)) {
            field.set(instance, sc.nextLine());
        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            field.set(instance, sc.nextInt());
            sc.nextLine();
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
            field.set(instance, sc.nextDouble());
            sc.nextLine();
        } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            field.set(instance, sc.nextBoolean());
            sc.nextLine();
        } else {
            System.out.println("Unsupported field type: " + fieldType.getSimpleName());

        }
    }

    private static void displayMethods(Method[] methods) {
        System.out.println("Methods:");
        for (Method method : methods) {
            String returnType = method.getReturnType().getSimpleName();
            String params = Arrays.toString(method.getParameterTypes());
            params = params.substring(1, params.length() - 1);
            System.out.printf("\t%s %s(%s)\n", returnType, method.getName(), params);
        }
    }

    private static void displayFields(Field[] fields) {
        System.out.println("Fields:");
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            System.out.printf("\t%s %s\n", type, field.getName());
        }
    }
}
