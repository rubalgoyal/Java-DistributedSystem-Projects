GRADLEW			?=	./gradlew
@port		?=	default_value
@address		?=	default_value
@certPath		?=	default_value
@keyPath		?=	default_value
@privateKey		?=	default_value
@maxNodes		?=	default_value

build:
		@echo	"build"
		$(GRADLEW)	clean	shadowJar

clean:
		@echo	"clean"
		$(GRADLEW)	clean

runServer:
		@echo	"run"
		java -cp build/libs/IdentityServer-1.0-SNAPSHOT-all.jar MasterServer $(@port) $(@address) $(@certPath) $(@keyPath) $(@maxNodes)

runClient:
		@echo	"run"
		java -cp build/libs/IdentityServer-1.0-SNAPSHOT-all.jar Client $(@address) $(@port) $(@certPath)

start-redis-server:
		@echo "Started Redis server at default port 6379"
		redis-server &

stop-server:
		- pkill -f "java -cp build/libs/IdentityServer-1.0-SNAPSHOT-all.jar MasterServer"

test:
		@echo "Running Test"
		$(GRADLEW)	test