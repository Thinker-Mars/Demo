export default function EatMeat(target, name, descriptor) {
  const eat = target.eat;
  descriptor.value = function() {
    eat();
    console.log('person eat meat');
  }
}