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
run-dist-files:
	./gradlew installDist
	./build/install/app/bin/app ./file1.json ./file2.json

