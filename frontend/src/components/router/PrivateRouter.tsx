import { useEffect } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const PrivateRouter = ({ isAuthenticated }: { isAuthenticated: boolean }) => {
	useEffect(() => {
		if (!isAuthenticated) {
			alert('로그인 후 이용해주세요');
		}
	}, [isAuthenticated]);

	return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default PrivateRouter;
