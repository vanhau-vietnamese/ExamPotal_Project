import PropTypes from 'prop-types';

Button.propTypes = {
  type: PropTypes.oneOf(['button', 'submit', 'reset']),
  children: PropTypes.node.isRequired,
  className: PropTypes.string,
  onClick: PropTypes.func,
};

function Button({ children, type = 'button', className, onClick }) {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`border-2 border-transparent text-center select-none transition-all duration-[350ms] font-semibold rounded-md ${className}`}
    >
      {children}
    </button>
  );
}

export default Button;
