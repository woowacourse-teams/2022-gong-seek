import { useNavigate } from 'react-router-dom';

import useSnackBar from '@/hooks/common/useSnackBar';
import useDeleteRefreshToken from '@/hooks/login/useDeleteRefreshToken';

function WithHooksHOC<F>(Component: React.ComponentType<F>) {
	return function Hoc(props: F) {
		const { showSnackBar } = useSnackBar();
		const navigate = useNavigate();
		const { mutate: mutateDeleteRefreshToken } = useDeleteRefreshToken();

		return (
			<Component
				{...props}
				showSnackBar={showSnackBar}
				navigate={navigate}
				mutateDeleteRefreshToken={mutateDeleteRefreshToken}
			/>
		);
	};
}

export default WithHooksHOC;
