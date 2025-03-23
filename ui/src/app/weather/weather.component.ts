import { Component, ElementRef, NgZone, AfterViewInit, ViewChild } from '@angular/core';
import { WeatherService } from '../services/weather.service';
import { WeatherForAddressResponse } from '../dto/WeatherForAddressResponse';

declare var google: any; // Fix for 'Cannot find name google'

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements AfterViewInit {
  @ViewChild('addressInput') addressInput: ElementRef; // Ensure it's correctly initialized
  address: string = '';
  weatherData?: WeatherForAddressResponse;
  errorMessage: string = '';
  addressText: string = '';

  constructor(private ngZone: NgZone,
              private weatherService: WeatherService) {}

  ngAfterViewInit() {
    this.initAutocomplete();
  }

  initAutocomplete() {
    // Check if addressInput is defined
    if (!this.addressInput) return;

    const autocomplete = new google.maps.places.Autocomplete(this.addressInput.nativeElement, {
      types: ['address']
    });

    autocomplete.addListener('place_changed', () => {
      this.ngZone.run(() => {
        const place = autocomplete.getPlace();
        if (place && place.formatted_address) {
          this.address = place.formatted_address;
        }
      });
    });
  }
  

  getWeather() {
    if (!this.address.trim()) {
      this.errorMessage = 'Please provide a valid address.';
      return;
    }

    this.weatherService.getWeatherByAddress(this.address).subscribe({
      next: (response) => {
        if (response.errors.length > 0) {
          this.errorMessage = response.errors.join(', ');
          this.weatherData = undefined;
        } else {
          this.weatherData = response;
          this.addressText = this.address;
          this.errorMessage = '';
        }
      },
      error: () => {
        this.errorMessage = 'Something went wrong. Please try again later.';
      }
    });
  }

  clearAddress() {
    this.address = '';
    this.weatherData = null;
    this.errorMessage = '';
  }

 /* getExtendedForecast() {
    this.weatherService.getExtendedForecast(this.address).subscribe((data: any) => {
      this.extendedForecast = data.list.slice(0, 5).map((forecast: any) => ({
        date: forecast.dt_txt,
        temp: forecast.main.temp,
        condition: forecast.weather[0].description
      }));
    });
  } */

  
}
