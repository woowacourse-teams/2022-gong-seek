import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';
import FallbackErrorBoundary from '@/components/helper/FallbackErrorBoundary';
import LogicErrorBoundary from '@/components/helper/LogicErrorBoundary';
import { worker } from '@/mock/browser';
import NotFound from '@/pages/NotFound';
import ServerErrorPage from '@/pages/ServerErrorPage';
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
	root.render(<ServerErrorPage />);
}

root.render(
	<ThemeProvider theme={theme}>
		<Global styles={reset} />
		<QueryClientProvider client={queryClient}>
			<RecoilRoot>
				<BrowserRouter>
					<FallbackErrorBoundary
						serverErrorFallback={<ServerErrorPage />}
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
