import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Qualification {
  degree: string;
  college: string;
  year: string;
}

interface User {
  name: string;
  email: string;
  phone: string;
  address: string;
  qualifications: Qualification[];
}

@Component({
  selector: 'app-test',
  imports: [CommonModule, FormsModule],
  templateUrl: './test.html',
  styleUrl: './test.css',
})
export class Test implements OnChanges, OnInit {
  @Input() inputValue1: { name: string } | undefined;
  qualifications: Qualification[] = [{ degree: '', college: '', year: '' }];
  user: User = {
    name: '',
    email: '',
    phone: '',
    address: '',
    qualifications: this.qualifications,
  };
  isFormSubmitted: boolean = false;
  a!: string;

  ngOnInit(): void {
    console.log('Test component is initialized');
  }

  constructor() {
    this.a = 'Test';
    console.log('Test component properties initialized');
  }

  addQualification(): void {
    this.qualifications.push({ degree: '', college: '', year: '' });
  }

  formSubmit(): void {
    console.log('Form submitted', this.user);
    this.isFormSubmitted = true;
  }

  back(): void {
    this.isFormSubmitted = false;
    this.user = {
      name: '',
      email: '',
      phone: '',
      address: '',
      qualifications: [{ degree: '', college: '', year: '' }],
    };
  }

  edit(): void {
    this.isFormSubmitted = false;
  }

  previousName: string | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    console.log('Input value changed:', changes['inputValue1'].currentValue);
  }

  ngDoCheck(): void {
    console.log('Change detection cycle triggered');
    if (this.inputValue1?.name != this.previousName) {
      console.log('Input value changed:', this.inputValue1?.name);
      this.previousName = this.inputValue1?.name.toString();
    }
  }
}
