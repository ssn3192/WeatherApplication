import { WeatherForAddressData } from "./WeatherForAddressData";

export interface WeatherForAddressResponse{
    fromCache: boolean;
    data: WeatherForAddressData;
    errors: string[];
}