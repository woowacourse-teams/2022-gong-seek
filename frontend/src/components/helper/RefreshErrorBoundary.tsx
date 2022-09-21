import CommonErrorBoundary from '@/components/helper/CommonErrorBoundary';
import CustomError from '@/components/helper/CustomError';
import {
	ErrorBoundaryProps,
	ErrorBoundaryState,
	LogicErrorBoundaryProps,
} from '@/components/helper/types/ErrorBoundary.type';
import { URL } from '@/constants/url';
import { isInValidTokenError } from '@/utils/confirmErrorType';
import WithHooksHOC from '@/utils/withHooksHOC';

class RefreshErrorBoundary extends CommonErrorBoundary<LogicErrorBoundaryProps> {
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

		if (isInValidTokenError(errorCode)) {
			localStorage.removeItem('accessToken');
			navigate(URL.LOGIN);
			showSnackBar('로그인을 진행해주세요');
			return;
		}

		throw new CustomError(errorCode);
	}
}

export default WithHooksHOC(RefreshErrorBoundary);
