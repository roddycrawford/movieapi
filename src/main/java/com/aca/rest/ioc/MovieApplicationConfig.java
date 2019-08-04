package com.aca.rest.ioc;

import org.glassfish.jersey.server.ResourceConfig;

public class MovieApplicationConfig extends ResourceConfig
{
	public MovieApplicationConfig()
	{
		System.out.println("....Calling constructor MovieApplicationConfig");
		this.register(new MovieDependencyBinder());
		this.packages(true, "com.aca.rest");
	}
}
