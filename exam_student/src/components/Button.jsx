import PropTypes from 'prop-types';

Button.propTypes = {
  type: PropTypes.oneOf(['button', 'submit', 'reset']),
  children: PropTypes.node.isRequired,
  className: PropTypes.string,
  disable: PropTypes.bool,
  onClick: PropTypes.func,
};

function Button({ children, type = 'button', className, onClick, disable }) {
  return (
    <button
      type={type}
      onClick={!disable && onClick}
      disabled={disable}
      className={`border-2 border-transparent text-center select-none transition-all duration-[350ms] font-semibold rounded-md disabled:opacity-50 ${className}`}
    >
      {children}
    </button>
  );
}

export default Button;
