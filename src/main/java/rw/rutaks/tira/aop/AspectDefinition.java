package rw.rutaks.tira.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectDefinition {
  @Pointcut("target(rw.rutaks.tira.controller.AuthController))")
  public void authEndpoints() {}

  @Pointcut("authEndpoints()")
  public void permittedEndpoints() {}

  @Pointcut("!permittedEndpoints()&&within(rw.rutaks.tira.controller..*)")
  public void secureEndpoints() {}
}
