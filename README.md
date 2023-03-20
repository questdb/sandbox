# Reproducer for Maven issue with Java Modules

## Intro

Objective: there is project `papa-app`. It is deployed as a library to Maven Central. Locally this can be done via:

```cmd
cd papa-app
mvn clean install
```

`papa-app` also deployes *test* infrastructure, which is there to be reused by projects that depend on `papa-app`.

There is `baby-app` project that depends on `papa-app`. A test in `baby-app` subclasses `AbstractTestSuperclass` aiming to reuse test infrastructure.

## The problem

When `baby-app` is a Java Module (e.g. includes own `module-info.java` Maven assembles incorrect command line for `javac`.  This can be reproduces via:

```cmd
cd baby-app
mvn clean test
```

You should be able to see :

```
[DEBUG] -d C:\Users\Vlad\dev\sandbox\baby-app\target\test-classes -classpath C:\Users\Vlad\dev\sandbox\baby-app\target\test-classes;C:\Users\Vlad\.m2\repository\junit\junit\4.13.2\junit-4.13.2.jar;C:\Users\Vlad\.m2\repository\org\ha
mcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;C:\Users\Vlad\.m2\repository\io\questdb\papa-app\1.0-SNAPSHOT\papa-app-1.0-SNAPSHOT-tests.jar; --module-path C:\Users\Vlad\dev\sandbox\baby-app\target\classes;C:\Users\Vlad\.m2\reposito
ry\io\questdb\papa-app\1.0-SNAPSHOT\papa-app-1.0-SNAPSHOT.jar; -sourcepath C:\Users\Vlad\dev\sandbox\baby-app\src\test\java;C:\Users\Vlad\dev\sandbox\baby-app\target\generated-test-sources\test-annotations; -s C:\Users\Vlad\dev\sand
box\baby-app\target\generated-test-sources\test-annotations -g -nowarn -target 11 -source 11 -encoding UTF-8 --patch-module com.questdb=C:\Users\Vlad\dev\sandbox\baby-app\target\classes;C:\Users\Vlad\dev\sandbox\baby-app\src\test\ja
va;C:\Users\Vlad\dev\sandbox\baby-app\target\generated-test-sources\test-annotations; --add-reads com.questdb=ALL-UNNAMED
```

Library `C:\Users\Vlad\.m2\repository\io\questdb\papa-app\1.0-SNAPSHOT\papa-app-1.0-SNAPSHOT-tests.jar` is added to `classpath` and not `modulepath`. This leads to compilation error:


```
[ERROR] COMPILATION ERROR :
[INFO] -------------------------------------------------------------
[ERROR] /C:/Users/Vlad/dev/sandbox/baby-app/src/test/java/com/questdb/AppTest.java:[5,18] cannot find symbol
  symbol:   class AbstractTestSuperclass
  location: package io.questdb
[ERROR] /C:/Users/Vlad/dev/sandbox/baby-app/src/test/java/com/questdb/AppTest.java:[11,30] cannot find symbol
  symbol: class AbstractTestSuperclass
[ERROR] /C:/Users/Vlad/dev/sandbox/baby-app/src/test/java/com/questdb/AppTest.java:[19,28] cannot find symbol
  symbol:   variable someUsefulState
  location: class com.questdb.AppTest
[ERROR] /C:/Users/Vlad/dev/sandbox/baby-app/src/test/java/com/questdb/AppTest.java:[20,20] cannot find symbol
  symbol:   method someUsefulMethod(java.lang.String)
  location: class com.questdb.AppTest
[INFO] 4 errors
```

