[![Build Status](https://travis-ci.org/bliblidotcom/template-plugin.svg?branch=master)](https://travis-ci.org/bliblidotcom/template-plugin)

Spring Boot Template Plugin
--------------------------

Add this dependency in your ```pom.xml```

```xml
<repositories>
    ...
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>bintray-bliblidotcom-maven</id>
      <name>bintray</name>
      <url>https://dl.bintray.com/bliblidotcom/maven</url>
    </repository>
    ...
</repositories>
```

```xml
<dependencies>
  ...
  <dependency>
   <groupId>com.blibli.oss</groupId>
   <artifactId>template-plugin</artifactId>
   <version>...</version>
 </dependency>
 ...
</dependencies>
```
