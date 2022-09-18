import CommonErrorBoundary, {
	ErrorBoundaryProps,
	ErrorBoundaryState,
} from '@/components/helper/CommonErrorBoundary';
import { LogicErrorBoundaryProps } from '@/components/helper/LogicErrorBoundary';
import { URL } from '@/constants/url';
import { isInValidTokenError } from '@/utils/confirmErrorType';
import WithHooksHOC from '@/utils/withHooksHOC';

class RefreshErrorBoudary extends CommonErrorBoundary<LogicErrorBoundaryProps> {
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
			navigate(URL.LOGIN);
		}

		showSnackBar('로그인을 진행해주세요');
	}
}

export default WithHooksHOC(RefreshErrorBoudary);