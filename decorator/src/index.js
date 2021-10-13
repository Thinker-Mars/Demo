import { EatMeat, Man } from './decorators/index.js';

@Man
class Person {

  @EatMeat
  eat() {
    console.log('person eat');
  }

}

/**
 * 测试作用于类的装饰器
 */
console.log(Person.sex);

/**
 * 测试作用于属性的装饰器
 */
const person1 = new Person();
person1.eat();