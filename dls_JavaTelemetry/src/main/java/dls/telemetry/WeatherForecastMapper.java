package dls.telemetry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import dls.telemetry.features.get.GetWeatherForecastResponse;

@Mapper
public interface WeatherForecastMapper {

	@Select("SELECT w.Id, w.[Date], w.[TemperatureC], w.[Summary] FROM dbo.WeatherForecast w")
	List<GetWeatherForecastResponse> findAllWeatherForecast();
}
