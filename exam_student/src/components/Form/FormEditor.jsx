import { useController } from 'react-hook-form';
import PropTypes from 'prop-types';
import { Editor } from '../TextEditor';

FormEditor.propTypes = {
  control: PropTypes.object.isRequired,
  name: PropTypes.string.isRequired,
  title: PropTypes.string,
  required: PropTypes.bool,
  error: PropTypes.string,
};

function FormEditor({ title, required, error, control, name }) {
  const { field } = useController({ name, control, defaultValue: '' });

  return (
    <div>
      <div className="flex items-center justify-between mb-1">
        <span className="block text-sm font-semibold font-body text-icon">
          {title} {required && <strong className="text-error">*</strong>}
        </span>
        {(error || !field.value) && (
          <p className="text-sm font-semibold pointer-events-none text-error">{error}</p>
        )}
      </div>
      <Editor
        data={field.value}
        onChange={field.onChange}
        className={`rounded-[5px] border ${error ? 'border-error' : 'border-slate-50'}`}
      />
    </div>
  );
}

export default FormEditor;
