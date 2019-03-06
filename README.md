# localstack-test

## run localstack
```
pip install --user localstack
```

## run localstack (Mac)
```
Library/Python/2.7/bin/localstack start
```

## list SQS queues
```
aws --endpoint-url http://localhost:4576 sqs list-queues
```

## create queue
```
aws --endpoint-url http://localhost:4576 sqs create-queue --queue-name=test-queue
```

## run localstack-test app
```
./gradlew run
```

## list queues subscribed by app
```
curl localhost:9000/list-queues
```

## send a message
```
aws --endpoint-url http://localhost:4576 sqs send-message --queue-url http://localhost:4576/123456789012/test-queue \
    --message-body '{"property1": "value-1"}' \
    --message-attributes '{"contentType": {"DataType": "String", "StringValue": "application/json"}}'
```
