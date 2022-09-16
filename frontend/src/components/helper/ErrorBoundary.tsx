import { Component } from 'react';

import CustomError from '@/components/helper/CustomError';
import { URL } from '@/constants/url';

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

	componentDidUpdate(_: never, prevState: State) {
		if (prevState.error !== this.state.error && this.state.error) {
			if (
				Number(this.state.error.errorCode) === 1001 ||
				Number(this.state.error.errorCode) === 1002 ||
				Number(this.state.error.errorCode) === 1003 ||
				Number(this.state.error.errorCode) === 1006 ||
				Number(this.state.error.errorCode) === 1009 ||
				Number(this.state.error.errorCode) === 2001
			) {
				alert('다시 로그인 해주세요');
				window.location.href = URL.LOGIN;
				return;
			}
			if (
				Number(this.state.error.errorCode) === 1004 ||
				Number(this.state.error.errorCode) === 1005
			) {
				window.location.href = URL.REFRESH_TOKEN_HANDLER;
				return;
			}
			if (Number(this.state.error.errorCode) === 3001) {
				window.location.href = URL.HOME;
				return;
			}
			localStorage.removeItem('accessToken');
		}

		if (this.state.error !== null && prevState.error !== null) {
			this.reset();
			return;
		}
		window.location.href = URL.HOME;
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
