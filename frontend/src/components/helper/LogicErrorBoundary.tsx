import { queryClient } from '../../index';
import CommonErrorBoundary, { ErrorBoundaryProps, ErrorBoundaryState } from './CommonErrorBoundary';
import CustomError from './CustomError';
import { NavigateFunction } from 'react-router-dom';

import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import WithHooksHOC from '@/utils/withHooksHOC';

interface LogicErrorBoundaryProps {
	showSnackBar?: (message: string) => void;
	navigate?: NavigateFunction;
}

class LogicErrorBoundary extends CommonErrorBoundary<LogicErrorBoundaryProps> {
	constructor(props: LogicErrorBoundaryProps & ErrorBoundaryProps) {
		super(props);
	}

	componentDidUpdate(_: never, prevState: ErrorBoundaryState) {
		if (prevState.error === this.state.error || !this.state.error) {
			return;
		}

		const { showSnackBar, navigate } = this.props;
		const errorCode = this.state.error.errorCode;
		if (
			typeof errorCode === 'undefined' ||
			typeof showSnackBar === 'undefined' ||
			typeof navigate === 'undefined'
		) {
			return;
		}
		const errorMessage = ErrorMessage[errorCode];
		if (
			errorCode === '1001' ||
			errorCode === '1002' ||
			errorCode === '1003' ||
			errorCode === '1006' ||
			errorCode === '1008' ||
			errorCode === '1009' ||
			errorCode === '2001'
		) {
			if (errorCode === '1008') {
				if (!confirm('로그인이 필요한 서비스입니다. 로그인 화면으로 이동하시겠습니까?')) {
					return;
				}
				//1008번일때만 사용자에게 확인요청
			}
			window.location.href = URL.LOGIN;
		}

		if (errorCode === '1004' || errorCode === '1005') {
			window.location.href = URL.REFRESH_TOKEN_HANDLER;
		}

		if (
			errorCode === '5002' ||
			errorCode === '5003' ||
			errorCode === '5009' ||
			errorCode === '5005'
		) {
			navigate(-1);
		}

		if (errorCode === '4001') {
			queryClient.invalidateQueries('comments');
		}

		showSnackBar(errorMessage);

		if (errorCode === '0000' || errorCode === '3001') {
			throw new CustomError(errorCode);
		}

		if (this.state.error !== null && prevState.error !== null) {
			this.reset();
		}
	}
}

export default WithHooksHOC(LogicErrorBoundary);
