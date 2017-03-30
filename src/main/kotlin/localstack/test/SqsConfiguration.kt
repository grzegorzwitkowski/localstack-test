package localstack.test

import com.amazonaws.services.sqs.AmazonSQS
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
@EnableSqs
open class SqsConfiguration : BeanFactoryAware {

    private var beanFactory: BeanFactory? = null

    override fun setBeanFactory(beanFactory: BeanFactory?) {
        this.beanFactory = beanFactory
    }

    @PostConstruct
    fun init() {
        val bean = beanFactory!!.getBean(AmazonSQS::class.java)
        println(bean)
    }
}