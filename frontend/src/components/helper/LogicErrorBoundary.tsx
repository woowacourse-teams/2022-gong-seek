import { isNotAccessVoteError, isExpiredTokenError } from '../../utils/confirmErrorType';

import CommonErrorBoundary from '@/components/helper/CommonErrorBoundary';
import CustomError from '@/components/helper/CustomError';
import {
	ErrorBoundaryProps,
	ErrorBoundaryState,
	LogicErrorBoundaryProps,
} from '@/components/helper/types/ErrorBoundary.type';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import { queryClient } from '@/index';
import {
	isAlreayLoginRefreshTokenError,
	isInvalidRefreshTokenError,
} from '@/utils/confirmErrorType';
import {
	isVoteError,
	isCommentError,
	isServerError,
	isNotFoundArticleError,
	isInValidTokenError,
	isAuthenticatedError,
} from '@/utils/confirmErrorType';
import WithHooksHOC from '@/utils/withHooksHOC';

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
			throw new CustomError('9999', '빠진 인자가 없는지 확인해주세요');
		}
		const errorMessage = ErrorMessage[errorCode];

		if (isAuthenticatedError(errorCode)) {
			if (errorCode === '1008') {
				if (!confirm('로그인이 필요한 서비스입니다. 로그인 화면으로 이동하시겠습니까?')) {
					return;
				}
			}
			navigate(URL.LOGIN);
		}

		if (isExpiredTokenError(errorCode)) {
			navigate(URL.REFRESH_TOKEN_HANDLER);
		}

		if (isVoteError(errorCode)) {
			navigate(-1);
		}

		if (isNotAccessVoteError(errorCode)) {
			navigate(URL.CATEGORY_DISCUSSION);
		}

		if (isCommentError(errorCode)) {
			queryClient.invalidateQueries('comments');
		}

		if (isAlreayLoginRefreshTokenError(errorCode)) {
			navigate(URL.HOME);
		}

		if (isInvalidRefreshTokenError(errorCode)) {
			localStorage.removeItem('accessToken');
			window.location.href = URL.HOME;
		}
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
