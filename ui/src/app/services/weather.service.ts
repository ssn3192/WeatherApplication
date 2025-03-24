import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WeatherForAddressResponse } from '../dto/WeatherForAddressResponse';

@Injectable()
export class WeatherService {
  private apiUrl = 'http://localhost:8088/api/weather'; // Update this URL based on your backend

  constructor(private http: HttpClient) {}


   /**
   * Get current weather for a given address
   * @param address User inputted address string
   */
   getWeatherByAddress(address: string): Observable<WeatherForAddressResponse> {
    const params = new HttpParams().set('address', address);
    return this.http.get<WeatherForAddressResponse>(this.apiUrl, { params });
  } 
}
