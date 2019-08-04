package com.aca.rest.ioc;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.aca.rest.dao.MovieDao;
import com.aca.rest.dao.MovieDaoImpl;
import com.aca.rest.dao.MovieDaoMock;
import com.aca.rest.dao.MovieDaoPrepStatementDemo;
import com.aca.rest.service.MovieService;

public class MovieDependencyBinder extends AbstractBinder
{
	
	@Override
	protected void configure()
	{
		System.out.println("....injecting concrete objects into movie app");
		this.bind(MovieService.class).to(MovieService.class);
//		this.bind(MovieDaoMock.class).to(MovieDao.class);
		this.bind(MovieDaoImpl.class).to(MovieDao.class);
//		this.bind(MovieDaoPrepStatementDemo.class).to(MovieDao.class);
		
	}
}
