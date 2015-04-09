import DS from 'ember-data';

// This is a simplication: this class represents both contexts and context groups
// It could be better implemented using a mixin
export default DS.Model.extend({
  // This class should contain exclusively label and items (for context groups),
  // or context (for contexts)
  label: DS.attr('string'),
  children: DS.hasMany('group', {inverse: null}),
  infoFile: DS.attr('string'),

  context: DS.belongsTo('context', { async: true }),

  root: DS.attr('boolean', {defaultValue: false}),

  isContext: function () {
    return this.get('context').get('content') !== null;
  }.property('context')
});
