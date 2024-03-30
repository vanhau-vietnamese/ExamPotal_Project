import PropTypes from 'prop-types';
import { useController } from 'react-hook-form';
import { Select } from '~/components';

export default function FormSelect({
  control,
  name,
  label,
  required,
  options = [],
  error,
  disabled,
}) {
  const { field } = useController({ control, name });
  return (
    <Select
      label={label}
      name={name}
      error={error}
      disabled={disabled}
      options={options}
      required={required}
      value={field.value}
      onChange={field.onChange}
    />
  );
}

FormSelect.propTypes = {
  control: PropTypes.object,
  name: PropTypes.string,
  label: PropTypes.string,
  options: PropTypes.array,
  required: PropTypes.bool,
  error: PropTypes.string,
  disabled: PropTypes.bool,
};
