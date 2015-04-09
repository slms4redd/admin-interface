import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  nativeName: DS.attr('string'),
  isSelected: DS.attr('boolean')
});
