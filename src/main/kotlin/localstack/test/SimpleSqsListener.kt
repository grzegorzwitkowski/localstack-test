package localstack.test

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class SimpleSqsListener {

    @SqsListener("test-queue")
    fun onMessage(msg: Map<String, Any>) {
        println(msg)
    }
}