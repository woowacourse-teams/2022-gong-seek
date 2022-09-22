import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';
import FallbackErrorBoundary from '@/components/helper/FallbackErrorBoundary';
import LogicErrorBoundary from '@/components/helper/LogicErrorBoundary';
import { worker } from '@/mock/browser';
import NotFound from '@/pages/NotFound';
import { theme } from '@/styles/Theme';
import { reset } from '@/styles/reset';
import { ThemeProvider, Global } from '@emotion/react';

// if (process.env.NODE_ENV === 'development') {
// 	worker.start();
// }

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

export const queryClient = new QueryClient();
let isUnhandledError = false;

window.addEventListener('unhandledrejection', () => {
	isUnhandledError = true;
});

if (isUnhandledError) {
	root.render(<div>알수 없는 에러가 발생하였습니다. 잠시후 다시 시도해주세요</div>);
}

root.render(
	<ThemeProvider theme={theme}>
		<Global styles={reset} />
		<QueryClientProvider client={queryClient}>
			<RecoilRoot>
				<BrowserRouter>
					<FallbackErrorBoundary
						serverErrorFallback={<div>서버 에러입니다!</div>}
						NotFoundErrorFallback={<NotFound />}
					>
						<LogicErrorBoundary>
							<App />
						</LogicErrorBoundary>
					</FallbackErrorBoundary>
				</BrowserRouter>
			</RecoilRoot>
		</QueryClientProvider>
	</ThemeProvider>,
);
