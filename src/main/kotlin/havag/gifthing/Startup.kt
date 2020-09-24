package havag.gifthing

import havag.gifthing.security.services.UserDetailsProvider
import org.springframework.context.support.BeanDefinitionDsl

class Startup : BeanDefinitionDsl({
	bean< UserDetailsProvider>()
})