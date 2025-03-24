import { ExtendedWeatherData } from "./ExtendedWeatherData";

export interface WeatherForAddressData{
    id: number;
    temp: number;
    high: number;
    low: number;
    description: string;
    ExtendedWeatherData:  ExtendedWeatherData;
}