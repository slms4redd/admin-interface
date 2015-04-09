import DS from 'ember-data';
import Ember from 'ember';

export default DS.Model.extend({
  label: DS.attr('string'), // will be localised
  baseURL: DS.attr('string'),
  wmsName: DS.attr('string'),
  imageFormat: DS.attr('string'),
  visible:  DS.attr('boolean'),
  legend: DS.attr('string'),
  sourceLink: DS.attr('string'),
  sourceLabel: DS.attr('string'), // will be localised
  rank: DS.attr('number'),
//   contexts: DS.hasMany('context', { async: true })

  computedLabel: function () {
    if (Ember.isEmpty(this.get('label'))) {
      return 'No label (%@)'.fmt(this.get('wmsName'));
    }
    return this.get('label');
  }.property('label')
});
