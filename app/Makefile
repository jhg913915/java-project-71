.DEFAULT_GOAL := build-run
clean:
	./gradlew clean
build:
	./gradlew clean build
install-dist:
	./gradlew clean installDist
run:
	./gradlew run
test:
	./gradlew test
report:
	./gradlew jacocoTestReport
lint:
	./gradlew checkstyleMain
checkstyle:
	./gradlew checkstyleMain
run-dist:
	./build/install/app/bin/app
run-dist-version:
	./build/install/app/bin/app -V
run-dist-help:
	./build/install/app/bin/app -h
run-dist-json-files:
	./gradlew installDist
	./build/install/app/bin/app ./src/test/resources/file1.json ./src/test/resources/file2.json
run-dist-yaml-files:
	./gradlew installDist
	./build/install/app/bin/app ./src/test/resources/file1.yaml ./src/test/resources/file2.yaml

