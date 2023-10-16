.PHONY: run clean debug

JAVA_HOME = java
TARGET = target/soundboard-*-SNAPSHOT-jar-with-dependencies.jar



$(TARGET):
	mvn package


run: $(TARGET)
	$(JAVA_HOME) -jar $(TARGET)


# TODO: Start the Dummy Server
debug:
	$(MAKE) clean
	$(MAKE) run


clean:
	mvn clean