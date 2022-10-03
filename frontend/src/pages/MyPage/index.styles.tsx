import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};

	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
	padding-left: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentContainer = styled.div`
	display: flex;
	flex-direction: column;

	min-height: ${({ theme }) => theme.size.SIZE_700};

	padding: ${({ theme }) => theme.size.SIZE_006};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: 0 ${({ theme }) => theme.size.SIZE_004} ${({ theme }) => theme.size.SIZE_004}
		${({ theme }) => theme.size.SIZE_004};
`;
