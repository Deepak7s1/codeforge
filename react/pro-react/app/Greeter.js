import React, {Component} from 'react';
import localeStr from './localeStr.json';

class Greeter extends Component {
  render() {
    return (
      <div>
        {localeStr.greetText}
      </div>
    );
  }
}

export default Greeter;
