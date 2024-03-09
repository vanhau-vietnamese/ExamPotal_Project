import PropTypes from 'prop-types';

ToolBarButton.propTypes = {
  icon: PropTypes.node.isRequired,
  isActive: PropTypes.bool,
  disabled: PropTypes.bool,
  onClick: PropTypes.func,
};

function ToolBarButton({ icon, isActive, onClick, disabled }) {
  return (
    <button
      className={`toolbar-button ${isActive ? 'active' : ''}`}
      disabled={disabled}
      onClick={onClick}
    >
      {icon}
    </button>
  );
}

export default ToolBarButton;
