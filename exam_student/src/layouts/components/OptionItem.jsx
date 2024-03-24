import PropTypes from 'prop-types';

export default function OptionItem(props) {
  return (
    <div className="">
      {props.options.map((option, index) => (
        <p key={index}>{option}</p>
      ))}
    </div>
  );
}

OptionItem.propTypes = {
  options: PropTypes.array.isRequired,
};
