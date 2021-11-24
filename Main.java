package com.company;
import java.lang.annotation.*;
import java.lang.reflect.Method;
public class Main {
    public static void main(String[] args) {
        System.out.println("Processing...");
        int success = 0;
        int failed = 0;
        int total = 0;
        int disabledRuntime = 0;
        Class<MethodsForMain> objectMain = MethodsForMain.class;
        //process for @CustomType annotation
        if (objectMain.isAnnotationPresent(CustomTypeAnnotation.class)) {
            Annotation annotationMain = objectMain.getAnnotation(CustomTypeAnnotation.class);
            CustomTypeAnnotation custom = (CustomTypeAnnotation) annotationMain;
            //listing all parts in annotation like a table
            System.out.printf("%nPriority: %s", custom.priority());
            System.out.printf("%nCreated By: %s", custom.createdBy());
            System.out.printf("%nTags: ");
            //setting a tag length and looping through each tag
            int tagLength = custom.tags().length;
            for (String tag : custom.tags()) {
                //if there are more than 1 tags add a coma
                if (tagLength > 1) {
                    System.out.printf(tag + ", ");
                } else {
                    //if there isn't more than one tag print the tag
                    System.out.printf(tag);
                }
                //decrement tagLength by 1
                tagLength--;
            }
            System.out.printf("%nLast Modified: %s%n", custom.lastModified());
            //process custom method annotation
            for (Method method : objectMain.getDeclaredMethods()) {
                //if method is annotated with custom method annotation
                if (method.isAnnotationPresent(CustomMethodAnnotation.class)) {
                    Annotation annotation1 = method.getAnnotation(CustomMethodAnnotation.class);
                    CustomMethodAnnotation customMethod = (CustomMethodAnnotation) annotation1;
                    //in enable = true (default)
                    if (customMethod.enabled()) {
                        String result = "n/a";
                        try {
                            result = (String) method.invoke(objectMain.newInstance());
                            System.out.printf("%s - Method '%s' - PROCESSED  \n",
                                    ++total,
                                    method.getName(),
                                    result);
                            success++;
                        } catch (Throwable ex) {
                            System.out.printf("%s - Method '%s' - DIDN'T PROCESS: %s %n",
                                    ++total,
                                    method.getName(),
                                    ex.getCause());
                            failed++;
                        }
                    } else {
                        System.out.printf("%s - Method '%s' - DIDN'T PROCESS \n",
                                ++total,
                                method.getName());
                        disabledRuntime++;
                    }
                }
            }
            System.out.printf("\nResult - ");
            System.out.printf("Total: "+total+", Successful: "+success+", Failed: "+failed+", Disabled: "+disabledRuntime+"\n \n \n");
        } // main
    }
}
