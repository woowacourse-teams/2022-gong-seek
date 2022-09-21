import { useNavigate } from 'react-router-dom';

import useSnackBar from '@/hooks/common/useSnackBar';

function WithHooksHOC<F>(Component: React.ComponentType<F>) {
	return function Hoc(props: F) {
		const { showSnackBar } = useSnackBar();
		const navigate = useNavigate();

		return <Component {...props} showSnackBar={showSnackBar} navigate={navigate} />;
	};
}

export default WithHooksHOC;
