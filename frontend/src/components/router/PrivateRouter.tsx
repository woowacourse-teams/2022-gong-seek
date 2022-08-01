import { useEffect } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

import useSnackBar from '@/hooks/useSnackBar';


const PrivateRouter = ({ isAuthenticated }: { isAuthenticated: boolean }) => {
	const {showSnackBar} = useSnackBar();
	useEffect(() => {
		if (!isAuthenticated) {
			showSnackBar('로그인 후 사용해주세요');
		}
	}, [isAuthenticated]);

	return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default PrivateRouter;
