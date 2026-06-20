import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Test } from './test/test';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Test, FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  inputValue1: { name: string } = { name: 'Prasath K' };
  protected readonly title = signal('ui');
  private name = 'Good';
  a: number = 10;
  b: number = 20;
  inputValue: string = 'Prasath';
  isDisabled: boolean = false;
  imagePath: string = 'assets/3.jpg';
  counter: number = 0;
  isShift: string = 'No';
  twoWayBindingValue: string = 'Two Way Binding';
  price: number = 100;
  quantity: number = 0;
  isDark: boolean = false;
  showContent: boolean = true;
  employees: string[] = ['Prasath', 'Kumar', 'John', 'David'];
  users: { name: string; age: number }[] = [
    { name: 'Prasath', age: 30 },
    { name: 'Kumar', age: 25 },
    { name: 'John', age: 28 },
    { name: 'David', age: 35 },
  ];
  age: string = '10';
  updateStyles: any = 'updateStyles';
  hasColor: boolean = false;
  enabled: boolean = false;
  color: any = { color: 'red' };
  authorized: boolean = false;
  username: string = '';
  renderRoot: boolean = false;

  increment(): void {
    this.counter++;
  }

  decrement(): void {
    this.counter--;
  }

  focusEvent(e: any): void {
    console.log('Input field focused', e);
  }

  keyDown(e: any): void {
    if (e.shiftKey) {
      this.isShift = 'Yes';
    }
    console.log('Key down event', e);
  }

  onArrowClick(e: any): void {
    console.log('Arrow Functin' + e);
    if (e.key === 'ArrowUp') {
      this.counter++;
    }
    if (e.key === 'ArrowDown') {
      this.counter--;
    }
  }

  getName(): string {
    return this.name;
  }

  onClick(): void {
    this.name = 'red';
    alert('Button clicked!');
  }

  toggle(): void {
    this.showContent = !this.showContent;
  }

  trackByFn(user: any): void {
    return user.id;
  }

  updateItem(): void {
    this.users[0].name = 'Prasath Updated';
  }

  updateAge(): void {
    this.age = '60';
  }

  currAge(): void {
    console.log('Current Age: ' + this.age);
  }

  setBackgroundColor(event: any): void {
    this.hasColor = event.target.value !== '';
    console.log('Background color set to: ' + event.target.value !== '');
  }

  toggleModes(): void {
    this.enabled = !this.enabled;
  }

  updateName(): void {
    this.inputValue1.name = 'Prasath Kumar';
  }
}
