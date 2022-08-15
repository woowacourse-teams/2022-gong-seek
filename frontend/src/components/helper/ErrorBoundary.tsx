import { Component } from 'react';

import CustomError from '@/components/helper/CustomError';
import { deleteRefreshCookie } from '@/utils/deleteRefreshCookie';

type Props = {
	fallback?: React.ReactNode;
	children?: React.ReactNode;
	enable: boolean;
};

type State = {
	error: CustomError | null;
};

class ErrorBoundary extends Component<Props, State> {
	constructor(props: Props) {
		super(props);
		this.state = { error: null };
	}

	reset() {
		this.setState({ error: null });
	}

	componentDidUpdate(_, prevState: State) {
		if (prevState.error !== this.state.error) {
			if (this.state.error && Number(this.state.error.errorCode) === 1004) {
				window.location.href = '/check-login';
			}
			if (this.state.error && Number(this.state.error.errorCode) === 1005) {
				window.location.href = '/check-login';
			}
			if (this.state.error && Number(this.state.error.errorCode) === 1008) {
				alert('다시 로그인 해주세요');
				deleteRefreshCookie();
				window.location.href = '/login';
			}
		}

		if (this.state.error !== null && prevState.error !== null) {
			this.reset();
		}
	}

	static getDerivedStateFromError(error: Error) {
		return { error: error };
	}

	render() {
		const { children, fallback } = this.props;

		if (this.state.error && this.props.enable) {
			return fallback;
		}

		return children;
	}
}

export default ErrorBoundary;
