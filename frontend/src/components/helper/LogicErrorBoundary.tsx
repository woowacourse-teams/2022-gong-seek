import { isRefreshTokenError } from '../../utils/confirmErrorType';
import { NavigateFunction } from 'react-router-dom';

import CommonErrorBoundary, {
	ErrorBoundaryProps,
	ErrorBoundaryState,
} from '@/components/helper/CommonErrorBoundary';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import { queryClient } from '@/index';
import {
	isVoteError,
	isCommentError,
	isServerError,
	isNotFoundArticleError,
	isInValidTokenError,
	isAuthenticatedError,
} from '@/utils/confirmErrorType';
import WithHooksHOC from '@/utils/withHooksHOC';

export interface LogicErrorBoundaryProps {
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

		if (isAuthenticatedError(errorCode)) {
			if (errorCode === '1008') {
				if (!confirm('로그인이 필요한 서비스입니다. 로그인 화면으로 이동하시겠습니까?')) {
					return;
				}
				//1008번일때만 사용자에게 확인요청
			}
			window.location.href = URL.LOGIN;
		}

		if (isInValidTokenError(errorCode)) {
			window.location.href = URL.REFRESH_TOKEN_HANDLER;
		}

		if (isVoteError(errorCode)) {
			navigate(-1);
		}

		if (isCommentError(errorCode)) {
			queryClient.invalidateQueries('comments');
		}

		if (isRefreshTokenError(errorCode)) {
			navigate(URL.HOME);
		}
		//위의 에러코드를 제외하고는 모두 snackBar만을 보여주도록 함.
		showSnackBar(errorMessage);

		if (isServerError(errorCode) || isNotFoundArticleError(errorCode)) {
			throw new CustomError(errorCode);
		}

		if (this.state.error !== null && prevState.error !== null) {
			this.reset();
		}
	}
}

export default WithHooksHOC(LogicErrorBoundary);