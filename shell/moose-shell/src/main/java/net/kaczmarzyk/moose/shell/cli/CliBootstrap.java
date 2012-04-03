package net.kaczmarzyk.moose.shell.cli;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import net.kaczmarzyk.moose.support.utils.ReflectionUtil;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.shell.CommandMarker;
import org.springframework.roo.shell.Converter;
import org.springframework.roo.shell.ExitShellRequest;
import org.springframework.roo.shell.event.ShellStatus;
import org.springframework.roo.support.logging.HandlerUtils;
import org.springframework.shell.JLineShellComponent;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;


public class CliBootstrap {

    private static CliBootstrap bootstrap;
    private JLineShellComponent shell;
    private ConfigurableApplicationContext ctx;
    private static StopWatch sw = new StopWatch("Moose Sehll");

    public static void main(String[] args) throws IOException {
        sw.start();        
        SimpleShellCommandLineOptions options = SimpleShellCommandLineOptions.parseCommandLine(args);

        ExitShellRequest exitShellRequest;
        try {
            bootstrap = new CliBootstrap("/META-INF/spring/applicationContext-shell.xml");
            exitShellRequest = bootstrap.run((String[])ReflectionUtil.get(options, "executeThenQuit"));
        } catch (RuntimeException t) {
            throw t;
        } finally {
            HandlerUtils.flushAllHandlers(Logger.getLogger(""));
        }

        System.exit(exitShellRequest.getExitCode());
    }

    public CliBootstrap(String applicationContextLocation) throws IOException {
        //setupLogging();

        Assert.hasText(applicationContextLocation, "Application context location required");
        createApplicationContext(applicationContextLocation);

        
        Map<String, JLineShellComponent> shells = ctx.getBeansOfType(JLineShellComponent.class);

        //Assert.isTrue(shells.size() == 1, "Exactly one Shell was required, but " + shells.size() + " found");
        //shell = shells.values().iterator().next();
        
        shell = new JLineShellComponent();
        


        Map<String, CommandMarker> commands = ctx.getBeansOfType(CommandMarker.class);

        for (CommandMarker command : commands.values()) {    
           System.out.println("Registgering command " + command);
           shell.getSimpleParser().add(command);
        }

        Map<String, Converter> converters = ctx.getBeansOfType(Converter.class);

        for (Converter converter : converters.values()) {
          System.out.println("Registgering converter " + converter);
          shell.getSimpleParser().add(converter);
        }  
        
        
        shell.start();
        //TODO use listener and latch..
        while(true) {
        	//System.out.println("shell status = " + shell.getShellStatus().getStatus());
        	if (shell.getShellStatus().getStatus().equals(ShellStatus.Status.USER_INPUT)) {
        		break;
        	} else {        		
    			try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }

        

    }

	private void createApplicationContext(String applicationContextLocation) {
		ctx = new ClassPathXmlApplicationContext(applicationContextLocation);
	}
	
	protected void createAndRegisterBeanDefinition(AnnotationConfigApplicationContext annctx, Class clazz) {
		RootBeanDefinition rbd = new RootBeanDefinition();
		rbd.setBeanClass(clazz);
		annctx.registerBeanDefinition(clazz.getSimpleName(), rbd);
	}

    protected ExitShellRequest run(String[] executeThenQuit) {
        
        ExitShellRequest exitShellRequest;
        
        if (null != executeThenQuit) {
            boolean successful = false;
            exitShellRequest = ExitShellRequest.FATAL_EXIT;

            for(String cmd : executeThenQuit) {
                successful = shell.executeCommand(cmd);
                if(!successful)
                    break;
            }

            //if all commands were successful, set the normal exit status
            if (successful) {
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }
        } else {
            shell.promptLoop();
            exitShellRequest = shell.getExitShellRequest();
            if (exitShellRequest == null) {
                // shouldn't really happen, but we'll fallback to this anyway
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }
        }

        ctx.close();
        sw.stop();
        if (shell.isDevelopmentMode()) {
            System.out.println("Total execution time: " + sw.getLastTaskTimeMillis() + " ms");
        }
        return exitShellRequest;
    }

}

